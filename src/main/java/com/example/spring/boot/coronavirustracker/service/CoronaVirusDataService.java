package com.example.spring.boot.coronavirustracker.service;

import com.example.spring.boot.coronavirustracker.model.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class CoronaVirusDataService {
    public static final String LOCATION_STATE_COLUMN = "Province/State";
    public static final String LOCATION_COUNTRY_COLUMN = "Country/Region";
    public static final String LOCATION_TOTAL_CASES = "Total cases";
    public static final String LOCATION_DIFFERENCE_TOTAL_CASES = "Changes since last day";
    private static final String CORONA_VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private static final boolean NOT_PARALLEL = false;
    private List<Location> locations;

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(CORONA_VIRUS_DATA_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .parse(new StringReader(response.body()));

        this.locations = getNewLocations(records).collect(toList());
    }

    public long totalCases() {
        return this.locations.stream()
                .mapToLong(Location::getTotalCases).sum();
    }

    public long differenceTotalCases() {
        return this.locations.stream()
                .mapToLong(Location::getDifferenceFromPreviousDay).sum();
    }

    public List<Location> getLocations() {
        return locations;
    }

    private Stream<Location> getNewLocations(Iterable<CSVRecord> records) {
        return stream(records.spliterator(), NOT_PARALLEL)
                .map(record -> {
                    long lastDayCases = Long.parseLong(record.get(record.size() - 1));
                    long prevDayCases = Long.parseLong(record.get(record.size() - 2));

                    return new Location(
                            record.get(LOCATION_STATE_COLUMN),
                            record.get(LOCATION_COUNTRY_COLUMN),
                            lastDayCases, lastDayCases - prevDayCases);
                });
    }
}
