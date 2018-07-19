package cloud.frizio.web.training.reactivedemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class ReactiveDemo {

    public static void main(String[] args) {
        System.out.println("Demo Reactive programming");

        //User user = new User("Max", 10000L);
        //System.out.println(user.toString());

        Observable<User> observable = new ReactiveDemo().getData(getUsers());
        observable.subscribe(
                System.out::println, // callback!!!
                throwable -> System.out.println("Excepiton " + throwable),
                () -> System.out.println("Completed!")
        );
    }


    Observable<User> getData(final List<User> userList) {
        return Observable.create( subscriber ->  {
            if(!subscriber.isUnsubscribed()) {
                userList.stream()
                        .forEach(users -> {
                            subscriber.onNext(users);
                            sleep(1000);
                            //subscriber.onError(new RuntimeException("WOOWWWW Exception"));
                        });
                }
            }
        );
    }


    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Alex", 7000L));
        users.add(new User("Bob", 2000L));
        users.add(new User("Cart", 3000L));
        return users;
    }


    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @AllArgsConstructor
    @Data
    @ToString
    static class User {
        private String name;
        private Long salary;
    }

}
