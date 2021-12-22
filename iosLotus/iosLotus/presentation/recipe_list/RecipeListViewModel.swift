//
//  RecipeListViewModel.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 12/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeListViewModel :ObservableObject{
    
    //dependencies
    
    let searchRecipes: SearchRecipes
    let foodCategoryUtil: FoodCategoryUtil
    
    @Published var state: RecipeListState = RecipeListState()
    @Published var showDialog: Bool = false

    
    init(searchRecipes:SearchRecipes, foodCategoryUtil: FoodCategoryUtil) {
        self.searchRecipes = searchRecipes
        self.foodCategoryUtil = foodCategoryUtil
        onTriggerEvent(stateEvent: RecipeListEvents.LoadRecipes())
        
        // TODO: perfom search
    }
    
    func onTriggerEvent(stateEvent: RecipeListEvents){
        switch stateEvent {
        case is RecipeListEvents.LoadRecipes:
            loadRecipes()
        case is RecipeListEvents.NewSearch:
            newSearch()
        case is RecipeListEvents.NextPage:
            nextPage()
        case is RecipeListEvents.OnRemoveHeadMessageFromQueue:
            removeHeadFromQueue()
        case is RecipeListEvents.OnUpdateQuery:
            updateState(query:(stateEvent as? RecipeListEvents.OnUpdateQuery)?.query)
        case is RecipeListEvents.OnSelectCategory:
            onSelectCategory(category: (stateEvent as? RecipeListEvents.OnSelectCategory)?.category)
        default:
            doNothing()
        }
    }
    
    
    func loadRecipes(){
        let currentState = self.state.copy() as! RecipeListState
         searchRecipes.execute(page:Int32(currentState.page) , query:currentState.query)
                .collectCommon(coroutineScope: nil, callback: {dataState in
                    if dataState != nil{
                        let data = dataState?.data
                        let message = dataState?.message
                        let loading = dataState?.isLoading
                        self.updateState(isLoading: loading)
                        if data != nil {
                            self.appendRecipes(recipes:data as! [Recipe])
                        }
                        if message != nil {
                            self.handleMessageByUIComponentType(message!.build())
                        }
                    }
                    
                })
        
    }
    private func appendRecipes(recipes: [Recipe]){
        var currentState = self.state.copy() as! RecipeListState
        var currentRecipes = currentState.recipes
        currentRecipes.append(contentsOf: recipes)
        self.state = self.state.doCopy(isLoading:currentState.isLoading,
                                       page: Int32(Int(currentState.page)),
                                       query: currentState.query,
                                       selectedCategory: currentState.selectedCategory,
                                       recipes: currentRecipes,
                                       bottomRecipe:currentState.bottomRecipe,
                                       queue:currentState.queue
        )
        currentState = self.state.copy() as! RecipeListState
        self.onUpdateBottomRecipe(recipe: currentState.recipes.last)
        
    }
    private func onUpdateBottomRecipe(recipe:Recipe?){
        recipe.flatMap { recipe in
            updateState(bottomRecipe: recipe)
        }
    }
    func nextPage(){
        let currentState = self.state.copy() as! RecipeListState
        updateState(page:Int(currentState.page)+1)
        loadRecipes()
    }
    
    func shouldQueryNextPage(recipe:Recipe)-> Bool{
        let currentState = self.state.copy() as! RecipeListState
        if recipe.id == currentState.bottomRecipe?.id{
            if(RecipeListState.Companion().RECIPE_PAGINATION_PAGE_SIZE*currentState.page<=currentState.recipes.count){
                if !currentState.isLoading {
                    return true
                }
            }
        }
        return false
    }
    
    private func newSearch(){
        reseatSearchState()
        loadRecipes()
    }
    private func onSelectCategory(category:FoodCategory?){
        updateState(query: category?.value ?? "",selectedCategory:category)
        newSearch()
        
    }
    private func reseatSearchState(){
        let currentState = (self.state.copy() as! RecipeListState)
        var foodCategory = currentState.selectedCategory
        if foodCategory?.value != currentState.query{
            foodCategory=nil
        }
        self.state = self.state.doCopy(isLoading:currentState.isLoading,
                                       page: 1,
                                       query: currentState.query,
                                       selectedCategory: foodCategory,
                                       recipes: [],
                                       bottomRecipe:currentState.bottomRecipe,
                                       queue:currentState.queue
        )
        
    }
    private func appendToQueue(message: GenericMessageInfo){
          let currentState = (self.state.copy() as! RecipeListState)
          let queue = currentState.queue
          let queueUtil = GenericInfoUtil() // prevent duplicates
          if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: message) {
              queue.add(element: message)
              updateState(queue: queue)
          }
      }
    
    func removeHeadFromQueue(){
          let currentState = (self.state.copy() as! RecipeListState)
          let queue = currentState.queue
          do {
              try queue.remove()
              updateState(queue: queue)
          }catch{
              print("\(error)")
          }
      }
      
      func shouldShowDialog(){
          let currentState = (self.state.copy() as! RecipeListState)
          showDialog = currentState.queue.items.count > 0
      }
      
    func handleMessageByUIComponentType(_ messageInfo: GenericMessageInfo){
        switch messageInfo.uiComponentType{
        case UIComponentType.Dialog():
            appendToQueue(message: messageInfo)
        case UIComponentType.None():
            print("\(messageInfo.description)")
        default:
            doNothing()
        }
    }
    func doNothing(){
        
    }
    func updateState(
        isLoading: Bool? = nil,
        page:Int? = nil,
        query:String? = nil,
        bottomRecipe: Recipe? = nil,
        selectedCategory: FoodCategory? = nil,
        queue :Queue<GenericMessageInfo>?=nil
    ){
        let currentState = self.state.copy() as! RecipeListState
        self.state = self.state.doCopy(isLoading: isLoading ?? currentState.isLoading,
                                       page: Int32(page ?? Int(currentState.page)),
                                       query: query ?? currentState.query,
                                       selectedCategory: selectedCategory ?? currentState.selectedCategory,
                                       recipes: currentState.recipes,
                                       bottomRecipe:bottomRecipe,
                                       queue:queue ?? currentState.queue
        )
        shouldShowDialog()
    
    }
}
