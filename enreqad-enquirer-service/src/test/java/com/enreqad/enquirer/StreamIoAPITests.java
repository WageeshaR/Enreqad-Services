package com.enreqad.enquirer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import io.getstream.client.Client;
import io.getstream.client.FlatFeed;
import io.getstream.client.NotificationFeed;
import io.getstream.core.KeepHistory;
import io.getstream.core.LookupKind;
import io.getstream.core.Region;
import io.getstream.core.models.*;
import io.getstream.core.options.*;

import java.net.MalformedURLException;

@SpringBootTest(classes = EnreqadEnquirerServiceApplicationTests.class)
public class StreamIoAPITests {

    public static final String apiKey = "fgraunjh8szq";
    public static final String apiSecret = "vqatw73ydyfwg5vqxa9txcb49uhf94d6vbfdywnk6bjpesjp7ns7zs6pc8jjxa2y";

    @Test
    void callApi() throws MalformedURLException {
        Client client = Client.builder(apiKey, apiSecret).build();

        FlatFeed chris = client.flatFeed("user", "User");
        // Add an Activity; message is a custom field - tip: you can add unlimited custom fields!
        try {
            chris.addActivity(
                    Activity.builder()
                            .actor("chris")
                            .verb("add")
                            .object("picture:10")
                            .foreignID("picture:10")
                            .extraField("message", "Beautiful bird!")
                            .build());

            // Create a following relationship between Jack's "timeline" feed and Chris' "user" feed:
            FlatFeed jack = client.flatFeed("timeline", "WageeshaR93");
            jack.follow(chris);

            // Read Jack's timeline and Chris' post appears in the feed:
            List<Activity> response = jack.getActivities().join();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
