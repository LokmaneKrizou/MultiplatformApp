//
//  RecipeDetailViewModel.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeDetailViewModel :ObservableObject{
    
    private let getRecipe: GetRecipe
    
    @Published var state: RecipeDetailState = RecipeDetailState()
    
    init(recipeId:Int,getRecipe:GetRecipe) {
        self.getRecipe = getRecipe
        onTriggerEvent(stateEvent: RecipeDetailEvents.LoadRecipe(id: Int32(recipeId)))
    }
    
    func onTriggerEvent(stateEvent: RecipeDetailEvents){
        switch(stateEvent){
        case is RecipeDetailEvents.LoadRecipe:
            loadRecipe(recipeId: (stateEvent as! RecipeDetailEvents.LoadRecipe).id)
        case is RecipeDetailEvents.OnRemoveHeadMessageFromQueue:
            doNothing()
        default: doNothing()
            
        }
    }
    
    func doNothing(){
        
    }
    private func loadRecipe(recipeId: Int32){
         self.getRecipe.execute(recipeId: recipeId).collectCommon(coroutineScope: nil, callback:{
                dataState in
                dataState.flatMap { it in
                    self.updateState(isLoading: it.isLoading)
                    
                    it.data.flatMap { recipe in
                        self.updateState(recipe: recipe)
                    }
                    it.message.flatMap { message in
                        self.handleMessageByUIComponentType(message.build())
                    }
                    
                }
            
            })
        
        }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo){
            // TODO("handle error messages")
        }

    private func updateState(
            isLoading: Bool? = nil,
            recipe: Recipe? = nil,
            queue: Queue<GenericMessageInfo>? = nil
        ){
            let currentState = (self.state.copy() as! RecipeDetailState)
            self.state = self.state.doCopy(
                isLoading: isLoading ?? currentState.isLoading,
                recipe: recipe ?? currentState.recipe,
                queue: queue ?? currentState.queue
            )
        }
    
}

