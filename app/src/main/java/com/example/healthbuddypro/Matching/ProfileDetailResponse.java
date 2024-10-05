package com.example.healthbuddypro.Matching;

public class ProfileDetailResponse {
    private int code;
    private String message;
    private Data data;

    public static class Data {
        private int userId;
        private String image;
        private String nickname;
        private String gender;
        private int age;
        private int workoutYears;
        private String[] favWorkouts;
        private String workoutGoal;
        private String region;
        private String bio;
        private int likeCount;

        // Getters and Setters
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getWorkoutYears() {
            return workoutYears;
        }

        public void setWorkoutYears(int workoutYears) {
            this.workoutYears = workoutYears;
        }

        public String[] getFavWorkouts() {
            return favWorkouts;
        }

        public void setFavWorkouts(String[] favWorkouts) {
            this.favWorkouts = favWorkouts;
        }

        public String getWorkoutGoal() {
            return workoutGoal;
        }

        public void setWorkoutGoal(String workoutGoal) {
            this.workoutGoal = workoutGoal;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }
    }

    // Getters and Setters for ProfileDetailResponse
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
