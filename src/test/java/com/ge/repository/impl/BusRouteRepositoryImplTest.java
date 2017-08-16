package com.ge.repository.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BusRouteRepositoryImplTest {
    @Test
    public void testExistsDirectRoute() {
        String dataPath = this.getClass().getResource("/data").getPath();
        BusRouteRepositoryImpl repository = new BusRouteRepositoryImpl(dataPath);
        repository.init();

        Assert.assertTrue(repository.existsDirectRoute(0, 1));
        Assert.assertTrue(repository.existsDirectRoute(1, 5));
        Assert.assertTrue(repository.existsDirectRoute(6, 4));
        Assert.assertFalse(repository.existsDirectRoute(6, 1));
        Assert.assertFalse(repository.existsDirectRoute(10, 20));
    }
}
