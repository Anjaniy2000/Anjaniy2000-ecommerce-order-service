package com.anjaniy.ecommerce_order_service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseIntegrationSpec extends Specification {
}
