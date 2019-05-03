#Author: Ankit sirmor
Feature: Validate new changes for AIB Personal Web Chat Form

# Validating Mortgage - Facebook dropdown option visibility in ROI Personal Web Chat Form.    
  
Scenario Outline: 01- Validate navigation to Branch locator from ROI personal Web Chat Form.

Given that customer navigates to web chat for for requesting a new Product
And is able to select "<Product>" from Product Requested dropdown

     Examples:
    | Product      | 
    | Mortgage - Facebook |