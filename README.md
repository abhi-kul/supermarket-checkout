# Supermarket

Implementation of a Supermarket checkout component.
This component might be used as part of a REST service to produce the receipt with total amount to pay for a list of items including discounts.

## Technology Stack

 - Java8
 - Junit
 - Mockito
 - Maven
 - IDE Used : Intellij
 
## How to Build and Run

The code can be compiled with command:

    mvn package
    
The tests can be executed with command:

    mvn verify

Once project verification is done , java docs will get generated into target folder.

## Architecture / Design 

 - Supermarket class : 
   - Supermarket class is a main component which accepts a list of discounts as constructor argument.
   - Supermarket class has a method checkout which accepts a basket and return a receipt.
 - Discount :
   - Two implementations of Discount interface are available at the moment and they represent discounts of type Buy N and Pay M or buy N for price X.
   - Multiple discounts might be available for the same item. The discount which maximizes the savings is preferred to other discounts for same items.
   - Limitation : Only one discount can be applied to an item.
 - Item :
   - Each item has properties such as label, price, quantity, discount, actual price, nominal cost, and actual cost.
   - Items are identified by the label.
 - Prices and quantity are represented with BidDecimal to have more control over rounding problems.
 - Entities such as Basket, Item and Receipt are immutable. Builders are provided to create those entities.
 - The checkout generate a receipt from a list of items where valid discounts are applied to maximize the savings

