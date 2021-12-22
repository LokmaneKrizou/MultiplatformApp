//
//  FoodCategoryChip.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 20/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct FoodCategoryChip: View {
    private let category:String
     private let isSelected:Bool
    
    init(category:String, isSelected:Bool = false) {
        self.category=category
        self.isSelected=isSelected
    }
    
    var body: some View {
        HStack{
            Text(category)
                .projectFont(style: .body,weight: .semibold)
                .padding(8)
                .foregroundColor(.onSecondary)
                .background(isSelected ? Color.neutral : Color.secondary)
                .cornerRadius(10)
        }
        
        
    }
}

struct FoodCategoryChip_Previews: PreviewProvider {
    static var previews: some View {
        FoodCategoryChip(category: "batata", isSelected: false)
    }
}
