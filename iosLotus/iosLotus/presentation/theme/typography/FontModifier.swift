//
//  SwiftUIView.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct FontModifier: ViewModifier {
    var style: UIFont.TextStyle = .body
    var weight: Font.Weight
    
    init(style:UIFont.TextStyle,weight: Font.Weight) {
        self.style = style
        self.weight = weight

    }
    init(style:UIFont.TextStyle) {
        self.style = style
        var weight:Font.Weight { switch style {
        case .title1 :
            return .bold
        case .title3 :
            return .medium
        case .caption2:
            return .light
        case .caption1:
            return .light
        case .largeTitle:
            return .black
        default:
            return .regular
        }
            
        }
      self.weight = weight
    }
    
  mutating func assignWeight(style: UIFont.TextStyle){
       
    }
   
    var fontFamily: String {
        switch style {
        case .body :
            return "lato_regular"
        case .caption1 , .caption2:
            return "lato_light"
        case .largeTitle:
            return "lato_black"
        default:
            return"lato_bold"
        }
        
    }
    func body(content: Content) -> some View {
        content
            .font(Font.custom(fontFamily, size: UIFont.preferredFont(forTextStyle: style).pointSize)
                    .weight(weight))

    }
    
}
