package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        LocalDate dateMeal = meals.get(0).getDateTime().toLocalDate();
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        caloriesMap.put(dateMeal,0);
        for(UserMeal userMeal : meals){
               caloriesMap.put(userMeal.getDateTime().toLocalDate(),caloriesMap.get(dateMeal)+userMeal.getCalories());

        }
        for(UserMeal userMeal: meals){
            LocalTime userMealTime=userMeal.getDateTime().toLocalTime();
            if(TimeUtil.isBetweenHalfOpen(userMealTime,startTime,endTime)){
                 userMealWithExcessList.add(createMeals(userMeal,caloriesMap.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
            }
        }
        return userMealWithExcessList;
    }

    private static UserMealWithExcess createMeals(UserMeal userMeal, boolean excess){
        return new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),excess);
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
