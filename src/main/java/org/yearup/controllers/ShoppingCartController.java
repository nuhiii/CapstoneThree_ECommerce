package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.Map;

// convert this class to a REST controller
@RestController
@RequestMapping("/cart")
// only logged in users should have access to these actions
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    public void addProductToCart(Principal principal, @PathVariable int productId) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            ShoppingCart cart = shoppingCartDao.getByUserId(userId);
            if (cart == null) {
                cart = new ShoppingCart();
            }

            if (cart.contains(productId)) {
                ShoppingCartItem item = cart.get(productId);
                item.setQuantity(item.getQuantity() + 1);
                shoppingCartDao.update(userId, productId, item.getQuantity());
            } else {
                Product product = productDao.getById(productId);
                ShoppingCartItem newItem = new ShoppingCartItem();
                newItem.setProduct(product);
                newItem.setQuantity(1);
                cart.add(newItem);
                shoppingCartDao.add(userId, productId, 1);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")
    public void updateProductInCart(Principal principal, @PathVariable int productId, @RequestBody Map<String, Integer> body) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            int quantity = body.get("quantity");

            ShoppingCart cart = shoppingCartDao.getByUserId(userId);
            if (cart == null || !cart.contains(productId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found in cart");
            }

            ShoppingCartItem item = cart.get(productId);
            item.setQuantity(quantity);

            shoppingCartDao.update(userId, productId, quantity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping
    public void clearCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.clear(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }

}
