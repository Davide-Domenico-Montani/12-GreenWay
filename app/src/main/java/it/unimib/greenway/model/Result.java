package it.unimib.greenway.model;

import java.util.List;
import java.util.Objects;

public class Result {
    private Result(){}

    public boolean isSuccessUser() {
        return this instanceof UserResponseSuccess;
    }

    public boolean isSuccessAirQuality() {
        return this instanceof AirQualityResponseSuccess;
    }
    public boolean isSuccessChallenge() {
        return this instanceof ChallengeResponseSuccess;
    }


    public boolean isSuccessRoutes(){
        return this instanceof RouteResponseSuccess;
    }

    public boolean isSuccessFriends(){
        return this instanceof FriendResponseSuccess;
    }
    public boolean isSuccessAllUsers(){
        return this instanceof AllUserResponseSuccess;
    }
    public boolean isError() {
        return this instanceof Error;
    }

    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
        }

    }


    public static final class AirQualityResponseSuccess extends Result {
        private final AirQualityResponse airQuality;
        public AirQualityResponseSuccess(AirQualityResponse airQuality) {
            this.airQuality = airQuality;
        }
        public AirQualityResponse getData() {
            return airQuality;
        }
    }

    public static final class RouteResponseSuccess extends Result {
        private final RoutesResponse routesResponse;
        public RouteResponseSuccess(RoutesResponse routeResponse) {
            this.routesResponse = routeResponse;
        }
        public RoutesResponse getData() {
            return routesResponse;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            RouteResponseSuccess that = (RouteResponseSuccess) obj;
            return Objects.equals(this.routesResponse, that.routesResponse);
        }

    }

    public static final class ChallengeResponseSuccess extends Result {
        private final ChallengeResponse challengeResponse;
        public ChallengeResponseSuccess(ChallengeResponse challengeResponse) {
            this.challengeResponse = challengeResponse;
        }
        public ChallengeResponse getData() {
            return challengeResponse;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Result.ChallengeResponseSuccess that = (Result.ChallengeResponseSuccess) obj;
            return Objects.equals(this.challengeResponse, that.challengeResponse);
        }
    }

    public static final class FriendResponseSuccess extends Result{
        private final List<User> friends;
        public FriendResponseSuccess(List<User> friends){
            this.friends = friends;
        }
        public List<User> getData(){
            return friends;
        }
    }


    public static final class AllUserResponseSuccess extends Result{
        private final List<User> allUser;
        public AllUserResponseSuccess(List<User> allUser){
            this.allUser = allUser;
        }
        public List<User> getData(){
            return allUser;
        }
    }

    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }


}
