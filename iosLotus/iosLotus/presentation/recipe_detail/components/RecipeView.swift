//
//  RecipeView.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import SDWebImageSwiftUI
import shared

struct RecipeView: View {
    private let recipe:Recipe
    private let dateUtil:DatetimeUtil
    
    init(recipe:Recipe, dateUtil:DatetimeUtil) {
        self.recipe=recipe
        self.dateUtil=dateUtil
    }
    
    
    var body: some View {
        ZStack{
        ScrollView{
            VStack(alignment:.leading){
                WebImage(url: URL(string: recipe.featuredImage))
                    .resizable()
                    .placeholder{
                        Rectangle().foregroundColor(.neutral)
                    }
                    .indicator(.activity)
                    .transition(.fade(duration: 0.5))
                    .scaledToFill()
                    .frame( height: 300, alignment: .center)
                    .clipped()
                VStack(alignment:.leading){
                    HStack(alignment:.lastTextBaseline){
                        VStack(alignment: .leading){
                            Text(recipe.title)
                                .projectFont(style: .title3)
                            Text("Updated at \(dateUtil.humanizeDatetime(date: recipe.dateUpdated )) by \(recipe.publisher) ")
                                .projectFont(style: .caption2)
                                .foregroundColor(.neutral)
                        }
                        Spacer()
                        Text("\(recipe.rating)")
                            .frame(alignment:.trailing)
                    }.padding(.vertical,8)
                        .padding(.horizontal,16)
                        
                    ForEach(recipe.ingredients as Array<String>,id:\.self){ ingredient in
                        Text(ingredient)
                            .padding(.top,4)
                        
                    }.listStyle(PlainListStyle())
                        .padding(.horizontal,16)
                }.background(Color.surface)
                    .padding(.vertical,8)
            }
        }.navigationBarTitleDisplayMode(.inline)
        }.background(Color.background)
    }
}

struct RecipeView_Previews: PreviewProvider {
    static var previews: some View {
        RecipeView(recipe: Recipe(id: 1, title: "test", publisher: "batata", featuredImage: "https://s.alicdn.com/@sc04/kf/H63c53eee05954941a431f47034b8e31aN.jpg_300x300.jpg", rating: 60, sourceUrl: "", ingredients: ["batata","tomatich"], dateAdded: DatetimeUtil().toLocalDate(date: 12004), dateUpdated: DatetimeUtil().toLocalDate(date: 12034)), dateUtil: DatetimeUtil())
    }
}
