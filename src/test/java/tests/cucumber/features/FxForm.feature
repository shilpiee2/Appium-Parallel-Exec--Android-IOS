Feature: Validate new changes for AIB Personal FX form

# Validating Limerick IT branch removal in ROI Personal FX form.    
Scenario Outline: 01- Validate navigation to Branch locator from ROI personal FX Form.

Given that customer wants to enquire contact details about "<branch>" for ROI personal FX Form and enters same in searchbox
And Enters "<branch>" details
Then customer is not provided with any "<branch>" information

     Examples:
    | branch      | 
    | Limerick Institute of Technology, Co. Limerick |

