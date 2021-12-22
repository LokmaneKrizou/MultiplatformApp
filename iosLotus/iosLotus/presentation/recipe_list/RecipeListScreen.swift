//
//  RecipeListScreen.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 12/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeListScreen: View {
    
    // dependencies
    private let networkModule: NetworkModule
    private let cacheModule: CacheModule
    private let searchRecipesModule: SearchRecipesModule
    private let foodCategories: [FoodCategory]
    @ObservedObject var viewModel: RecipeListViewModel
    
    init(
        networkModule:NetworkModule,
        cacheModule:CacheModule) {
            self.networkModule = networkModule
            self.cacheModule = cacheModule
            self.searchRecipesModule =
            SearchRecipesModule(
                networkModule : self.networkModule,
                cacheModule:  self.cacheModule
            )
            let foodCategoryUtil = FoodCategoryUtil()
            
            self.viewModel = RecipeListViewModel(
                searchRecipes: searchRecipesModule.searchRecipes,
                foodCategoryUtil: FoodCategoryUtil()
            )
            self.foodCategories = foodCategoryUtil.getAllFoodCategories()
        }
    
    var body: some View {
        NavigationView{
            ZStack{
                VStack{
                    SearchAppBar(query: viewModel.state.query,
                                 foodCategories:foodCategories,
                                 selectedCategory: viewModel.state.selectedCategory, onTriggerEvent: viewModel.onTriggerEvent)
                    List{
                        ForEach(viewModel.state.recipes,id:\.self.id){ recipe in
                            ZStack{
                                RecipeCard(recipe: recipe)
                                    .onAppear(perform:  {
                                        if viewModel.shouldQueryNextPage(recipe: recipe) {
                                            viewModel.onTriggerEvent(stateEvent: RecipeListEvents.NextPage())
                                        }
                                    })
                                NavigationLink(destination: RecipeDetailScreen(recipeId: Int(recipe.id), cacheModule: cacheModule, networkModule: networkModule)){EmptyView()}
                            }
                            .listRowInsets(EdgeInsets())
                            .padding(.top,10)
                            .background(Color.background)
                        }
                    }.listStyle(PlainListStyle())
                        .padding(.vertical,6)
                        .padding(.horizontal,16)
                    
                }
                if viewModel.state.isLoading{
                    ProgressView("Searching recipes...")
                }
            }.navigationBarHidden(true)
                .background(Color.background)
        }.accentColor(.secondary)
            .alert(isPresented: $viewModel.showDialog) {
                let first = viewModel.state.queue.peek()!
                return GenericAlert().build(message: first) {
                    viewModel.onTriggerEvent(stateEvent: RecipeListEvents.OnRemoveHeadMessageFromQueue())
                }
            }
    }
}


