package com.outsourcing.outsourcingproject.domain.order.entity;

import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

public record OrderEntities(User user, Store store, Menu menu) {
}
