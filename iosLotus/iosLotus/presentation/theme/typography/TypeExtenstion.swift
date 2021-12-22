//
//  Type.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 21/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

extension Text {
    func projectFont(style: UIFont.TextStyle, weight: Font.Weight?=nil) ->  some View {
        if weight != nil{
            return self.modifier(FontModifier(style: style, weight: weight!))
        }else{
            return self.modifier(FontModifier(style: style))
        }
    }
}
