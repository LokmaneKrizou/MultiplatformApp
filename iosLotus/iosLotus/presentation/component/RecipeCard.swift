//
//  RecipeCard.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared
import SDWebImageSwiftUI
struct RecipeCard: View {
    
    private let recipe: Recipe
    
    init(recipe:Recipe) {
        self.recipe = recipe
    }
    var body: some View {
        VStack(alignment:.leading){
            WebImage(url: URL(string: recipe.featuredImage))
                .resizable()
                .indicator(.activity)
                .transition(.fade(duration: 0.5))
                .scaledToFill()
                .frame( height: 250, alignment: .center)
                .clipped()
            HStack(alignment: .lastTextBaseline){
                Text(recipe.title)
                    .projectFont(style: .body)
                    .frame(alignment: .center)
                Spacer()
                Text(String(recipe.rating))
                    .frame(alignment:.trailing)
                
            }.padding(.top,8)
                .padding(.leading,8)
                .padding(.trailing,8)
                .padding(.bottom,12)
            
        }
        .cornerRadius(8)
        .shadow(radius: 2)
        .background(Color.surface)
    }
}

struct RecipeCard_Previews: PreviewProvider {
    static var previews: some View {
        RecipeCard(recipe: Recipe(id: 1, title: "test", publisher: "batata", featuredImage: "https://s.alicdn.com/@sc04/kf/H63c53eee05954941a431f47034b8e31aN.jpg_300x300.jpg", rating: 60, sourceUrl: "", ingredients: ["batata","tomatich"], dateAdded: DatetimeUtil().toLocalDate(date: 12004), dateUpdated: DatetimeUtil().toLocalDate(date: 12034)))
    }
}
