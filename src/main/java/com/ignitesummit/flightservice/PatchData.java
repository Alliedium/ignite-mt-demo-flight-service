package com.ignitesummit.flightservice;

import com.ignitesummit.flightservice.actions.FilterFieldsAction;
import io.github.alliedium.ignite.migration.patchtools.*;
import io.github.alliedium.ignite.migration.util.PathCombine;
import org.apache.beam.sdk.values.Row;

import java.nio.file.Paths;
import java.util.List;

public class PatchData {
    public static void main(String[] args) {
        PathCombine source = new PathCombine(Paths.get("./backup"));
        PathCombine destination = new PathCombine(Paths.get("./patchedData"));

        PatchContext patchContext = new PatchContext(source, destination);

        patchContext.prepare();

        patchContext.patchCachesWhichEndWith("flightsProfitCache", flightProfitCachePath -> {
            String flightCachePath = flightProfitCachePath.substring(0, flightProfitCachePath.lastIndexOf("/") + 1)
                    + "flightsCache";
            TransformAction<TransformOutput> flightProfitAction = new SelectAction.Builder()
                    .fields("key", "id", "expense", "income")
                    .context(patchContext)
                    .from(flightProfitCachePath)
                    .build();

            TransformAction<TransformOutput> flightAction = new SelectAction.Builder()
                    .fields("id", "passengerList")
                    .context(patchContext)
                    .from(flightCachePath)
                    .build();

            TransformAction<TransformOutput> action = new JoinAction.Builder().join(flightProfitAction, flightAction).on("id").build();

            action = new CopyFieldAction.Builder()
                    .action(action)
                    .copyField("expense", "passengersCount")
                    .build();

            action = new MapAction.Builder()
                    .action(action)
                    .map(row -> {
                        List<Row> passengerList = row.getValue("passengerList");

                        return Row.fromRow(row)
                                .withFieldValue("passengersCount", passengerList.size())
                                .build();
                    }).build();

            action = new FilterFieldsAction(action, "key", "id", "expense", "income", "passengersCount");

            String cacheName = flightProfitCachePath.substring(flightProfitCachePath.lastIndexOf("/"));
            new CacheWriter(action).writeTo(destination.plus(cacheName).getPath().toString());
        });

        patchContext.getPipeline().run().waitUntilFinish();
        patchContext.copyAllNotTouchedFilesToOutput();
    }
}
