package com.anjaniy.marutinandan_restaurant_api

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseIntegrationSpec extends Specification {
}
