package com.ignitesummit.flightservice.actions;

import io.github.alliedium.ignite.migration.patchtools.TransformAction;
import io.github.alliedium.ignite.migration.patchtools.TransformOutput;
import org.apache.avro.Schema;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.beam.sdk.schemas.transforms.Select.fieldNames;

public class FilterFieldsAction implements TransformAction<TransformOutput> {

    private final String[] fields;
    private final TransformAction<TransformOutput> action;

    public FilterFieldsAction(TransformAction<TransformOutput> action, String... fields) {
        this.fields = fields.clone();
        this.action = action;
    }

    @Override
    public TransformOutput execute() {
        TransformOutput output = action.execute();

        PCollection<Row> rows = output.getPCollection().apply(fieldNames(fields));
        Schema updatedSchema = filterSchemaFields(output.getSchema(), fields);

        return new TransformOutput.Builder()
                .setPCollection(rows)
                .setFields(fields)
                .setSchema(updatedSchema)
                .setCacheComponents(output.getCacheComponents())
                .setCacheConfiguration(output.getCacheConfiguration())
                .setQueryEntities(output.getQueryEntities())
                .setCacheDataTypes(output.getCacheDataTypes())
                .build();
    }

    private static Schema filterSchemaFields(Schema schema, String... fields) {
        List<Schema.Field> schemaFields = new ArrayList<>(schema.getFields());
        Set<String> fieldSet = Stream.of(fields).collect(Collectors.toSet());

        Stream<Schema.Field> stream = schemaFields.stream();
        if (fieldSet.size() > 0) {
            stream = stream.filter(field -> fieldSet.contains(field.name()));
        }

        Set<Schema.Field> pipelineSchemaFields = stream
                .map(field -> new Schema.Field(field.name(), field.schema(), field.doc(), field.defaultVal(), field.order()))
                .collect(Collectors.toSet());

        return Schema.createRecord(
                schema.getName(),
                schema.getDoc(),
                schema.getNamespace(), false,
                new ArrayList<>(pipelineSchemaFields));
    }
}
