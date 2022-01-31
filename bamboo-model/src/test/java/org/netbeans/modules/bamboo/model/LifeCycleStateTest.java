/*
 * Copyright 2022 NetBeans.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 * Unit Test for {@link LifeCycleState}
 * @author Mario Schroeder
 */
class LifeCycleStateTest {

    @Test
    @DisplayName("It should have a method that return a lifecycle for a successful job.")
    void testGetSuccessfulBambooLifeCycle() {
        List<LifeCycleState> result = LifeCycleState.getSuccessfulBambooLifeCycle();
        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals(LifeCycleState.Finished, result.get(3))
        );
    }

    @Test
    @DisplayName("It should have a method that return a lifecycle for a failed job.")
    public void testGetFailedBambooLifeCycle() {
        List<LifeCycleState> result = LifeCycleState.getFailedBambooLifeCycle();
        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals(LifeCycleState.NotBuilt, result.get(3))
        );
    }
    
}
