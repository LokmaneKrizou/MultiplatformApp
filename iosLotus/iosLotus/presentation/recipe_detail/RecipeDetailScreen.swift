//
//  RecipeDetailScreen.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeDetailScreen: View {
    
    private let cacheModule: CacheModule
    private let networkModule: NetworkModule
    private let getRecipeModule: GetRecipeModule
    private let recipeId: Int
    private let datetimeUtil = DatetimeUtil()
    
    @ObservedObject var viewModel: RecipeDetailViewModel
    
    init(
        recipeId: Int,
        cacheModule: CacheModule,
        networkModule: NetworkModule
         ) {
        self.recipeId = recipeId
        self.cacheModule = cacheModule
        self.networkModule = networkModule
        self.getRecipeModule = GetRecipeModule(
            networkModule: self.networkModule,
            cacheModule: self.cacheModule
            
        )
        viewModel = RecipeDetailViewModel(
            recipeId: self.recipeId,
            getRecipe: self.getRecipeModule.getRecipe
        )
    }
    
    var body: some View {
        if viewModel.state.recipe != nil {
            RecipeView(recipe: viewModel.state.recipe!, dateUtil: datetimeUtil)
              }else{
                  Text("Unable to retrieve the recipe details.")
              }
    }
}
