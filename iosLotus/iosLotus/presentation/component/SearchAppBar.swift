//
//  SearchAppBar.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 14/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SearchAppBar: View {
    
    @State var query:String
    var selectedCategory: FoodCategory?
    private let foodCategories: [FoodCategory]
    private let onTriggerEvent: (RecipeListEvents)-> Void
    init(query:String,foodCategories:[FoodCategory], selectedCategory:FoodCategory?,onTriggerEvent:@escaping(RecipeListEvents)->Void) {
        self._query=State(initialValue: query)
        self.onTriggerEvent = onTriggerEvent
        self.foodCategories=foodCategories
        self.selectedCategory = selectedCategory
    }
    var body: some View {
        VStack{
            HStack{
                Image(systemName: "magnifyingglass")
                TextField("Search...", text: $query,onCommit: {
                    onTriggerEvent(RecipeListEvents.NewSearch())
                }).onChange(of: query, perform: { value in
                    onTriggerEvent(RecipeListEvents.OnUpdateQuery(query:value))
                }).foregroundColor(.onPrimary)
            }.padding(.bottom,8)
            ScrollView(.horizontal){
                HStack(spacing:10){
                    ForEach(foodCategories,id:\.self.value){ category in
                        FoodCategoryChip(category: category.value, isSelected:category==selectedCategory)
                            .onTapGesture {
                                query = category.value
                                onTriggerEvent(RecipeListEvents.OnSelectCategory(category:category))
                        }
                    }
                }
                
            }
        }.padding(.vertical,8).padding(.horizontal,8).background(Color.primary)
    }
}

struct SearchAppBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchAppBar(query: "batata",foodCategories: FoodCategoryUtil().getAllFoodCategories(),selectedCategory: FoodCategory.chicken) { RecipeListEvents in
            
        }
    }
}
