package com.anjaniy.ecommerce_order_service.service

import com.anjaniy.ecommerce_order_service.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired


class UserServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private UserService userService;

    def 'Should get users' () {
        given:
        when:
        def users = userService.getUsers()
        then:
        assert users.size() == 5
    }

    def 'Should get user with ID' () {
        given:
        def userId = 3
        when:
        def user = userService.getUser(userId)
        then:
        assert user.firstName == 'Raj'
        assert user.lastName == 'Patel'
        assert user.email == 'raj.patel@example.com'

    }
}
