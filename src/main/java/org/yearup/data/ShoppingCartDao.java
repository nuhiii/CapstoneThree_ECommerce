package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    void add(int userId, int productId, int quantity);
    void update(int userId, int productId, int quantity);
    void clear(int userId);
}
