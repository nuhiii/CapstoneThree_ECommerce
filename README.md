# EasyShop Java Spring Boot Application

This Spring Boot application serves as an e-commerce platform. It allows users to register, manage their profiles, browse products, manage their shopping carts, and process orders. It also has administrative controls.

## Features
- **User Registration and Authentication**: Allows admin control or users to register and log in to their account.
- **Profile Management**: Users can view and update their profile information.
- **Product Browsing and Searching**: Users can browse and search for products by category or additional filters.
- **Shopping Cart Management**: Users can add products to their cart, update quantities, and clear their cart of items, with prices adjusted accordingly.
- **Order Processing (WORK IN PROGRESS)**: Users can process their shopping cart into a total order to check out.

- ## Project Structure
- **EasyShopApplication.java**: Entry point of the application. Configures and runs the Spring Boot application.

- **controllers**
  - **AuthenticationController.java**: Handles admin / user authentication roles.
  - **ProfileController.java**: Manages user profile viewing and updating.
  - **ProductsController.java**: Handles product viewing and functionalities.
  - **CategoriesController.java**: Manages product categories.
  - **ShoppingCartController.java**: Manages the shopping cart related operations.

- **data**
  - **ProfileDao.java**: Interface for profile data access operations.
  - **UserDao.java**: Interface for user data access operations.
  - **ProductDao.java**: Interface for product data access operations.
  - **CategoryDao.java**: Interface for category data access operations.
  - **ShoppingCartDao.java**: Interface for shopping cart data access operations.

- **data.mysql**
  - **MySqlProfileDao.java**: MySQL implementation of ProfileDao.
  - **MySqlUserDao.java**: MySQL implementation of UserDao.
  - **MySqlProductDao.java**: MySQL implementation of ProductDao.
  - **MySqlCategoryDao.java**: MySQL implementation of CategoryDao.
  - **MySqlShoppingCartDao.java**: MySQL implementation of ShoppingCartDao.

- **models**
  - **Profile.java**: Represents a user profile.
  - **User.java**: Represents a user.
  - **Product.java**: Represents a product.
  - **Category.java**: Represents a product category.
  - **ShoppingCart.java**: Represents a shopping cart.
  - **ShoppingCartItem.java**: Represents an item in the shopping cart.

- **security files**

## Future Improvements
- Complete Order Check Out
- Build on the Front End UI/UX Design
- Allow smoother navigation
- Ensure seamless back end integration
- Create multiple profiles
