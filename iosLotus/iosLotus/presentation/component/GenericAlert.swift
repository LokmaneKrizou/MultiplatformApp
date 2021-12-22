//
//  GenericAlert.swift
//  iosLotus
//
//  Created by Lokmane Krizou on 22/12/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GenericAlert {
    
    func build(message:GenericMessageInfo,onRemoveHeadMessage:@escaping()->Void)-> Alert{
        if (message.positiveAction  != nil && message.negativeAction != nil) {
            return Alert(
                title: Text(message.title),
                message: Text(message.description_ ?? "Something went wrong"),
                primaryButton: .default(Text(message.positiveAction!.positiveBtnTxt)),
                secondaryButton: .cancel(Text(message.negativeAction!.negativeBtnTxt))
            )
        }else {
            return Alert(title: Text(message.title),
                         message: Text(message.description_ ?? "Something went wrong"),
                         dismissButton: message.positiveAction != nil ? .default(Text(message.positiveAction!.positiveBtnTxt),action: {
                message.positiveAction!.onPositiveAction()
                onRemoveHeadMessage()
                
            }) :
                            message.negativeAction != nil ? .cancel(Text(message.negativeAction!.negativeBtnTxt), action: {
                
                message.negativeAction!.onNegativeAction()
                onRemoveHeadMessage()
            }):
                                .default(Text("OK"),action: {onRemoveHeadMessage()})
                         
                         
            )
        }
        
        
    }
}
