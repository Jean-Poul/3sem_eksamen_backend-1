package jokefetcher;

import java.io.IOException;
import utils.HttpUtils;

public class NextLaunchFetcher {

    public static void main(String[] args) throws IOException {

        String rocket = HttpUtils.fetchData("https://ll.thespacedevs.com/2.0.0/launch/upcoming?format=json");

        System.out.println("JSON fetched from rocket API:");
        System.out.println(rocket);

    }
    
    public String getNextLaunchJson() throws IOException{
        String rocket = HttpUtils.fetchData("https://ll.thespacedevs.com/2.0.0/launch/upcoming?format=json");
        return rocket;
    }
}
