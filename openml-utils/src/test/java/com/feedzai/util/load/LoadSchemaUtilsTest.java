/*
 * Copyright 2018 Feedzai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.feedzai.util.load;

import com.feedzai.openml.data.schema.DatasetSchema;
import com.feedzai.openml.provider.exception.ModelLoadingException;
import com.feedzai.util.load.LoadSchemaUtils;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

/**
 * Tests for the {@link LoadSchemaUtils}.
 *
 * @author Paulo Pereira (paulo.pereira@feedzai.com)
 * @since 0.1.0
 */
public class LoadSchemaUtilsTest {

    /**
     * Tests that is possible to get the {@link DatasetSchema} from a json file.
     *
     * @throws ModelLoadingException In case of error loading the schema.
     */
    @Test
    public void schemaFromJsonTest() throws ModelLoadingException {
        final String directoryPath = getClass().getResource(File.separator + "random_forest").getPath();
        final DatasetSchema datasetSchema = LoadSchemaUtils.datasetSchemaFromJson(Paths.get(directoryPath));

        assertThat(datasetSchema.getFieldSchemas())
                .as("number of fields of the schema")
                .hasSize(4);
    }

    /**
     * Tests that when there is an error with the json file it will return an empty optional.
     */
    @Test
    public void notExistTest() {
        assertThatThrownBy(() -> LoadSchemaUtils.datasetSchemaFromJson(Paths.get("dummy")))
                .as("the dataset schema doesn't exist")
                .hasMessageContaining("The path")
                .hasMessageContaining("should be a directory");
    }
}
