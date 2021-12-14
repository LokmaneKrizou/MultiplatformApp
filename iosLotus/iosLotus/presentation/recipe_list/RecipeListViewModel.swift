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
            doNothing()
        case is RecipeListEvents.NextPage:
            nextPage()
        case is RecipeListEvents.OnRemoveHeadMessageFromQueue:
            doNothing()
        case is RecipeListEvents.OnUpdateQuery:
            doNothing()
        default:
            doNothing()
        }
    }
    
    func loadRecipes(){
        let currentState = self.state.copy() as! RecipeListState
        do{
            try searchRecipes.execute(page:Int32(currentState.page) , query:currentState.query)
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
        }catch{
            print("\(error)")
        }
        
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
    func handleMessageByUIComponentType(_ messageInfo: GenericMessageInfo){
        
    }
    func doNothing(){
        
    }
    func updateState(
        isLoading: Bool? = nil,
        page:Int? = nil,
        query:String? = nil,
        bottomRecipe: Recipe? = nil,
        queue :Queue<GenericMessageInfo>?=nil
    ){
        let currentState = self.state.copy() as! RecipeListState
        
        self.state = self.state.doCopy(isLoading: isLoading ?? currentState.isLoading,
                                       page: Int32(page ?? Int(currentState.page)),
                                       query: query ?? currentState.query,
                                       selectedCategory: currentState.selectedCategory,
                                       recipes: currentState.recipes,
                                       bottomRecipe:bottomRecipe,
                                       queue:queue ?? currentState.queue
        )
    }
}
