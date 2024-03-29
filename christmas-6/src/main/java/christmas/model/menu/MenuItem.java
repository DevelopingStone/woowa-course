package christmas.model.menu;

import christmas.model.discount.DiscountDay;
import java.util.Arrays;
import java.util.Objects;

public enum MenuItem {

    // 애피타이저
    MUSHROOM_SOUP(MenuCategory.APPETIZER, "양송이수프", 6000, false, false),
    TAPAS(MenuCategory.APPETIZER, "타파스", 5500, false, false),
    CAESAR_SALAD(MenuCategory.APPETIZER, "시저샐러드", 8000, false, false),

    // 메인
    T_BONE_STEAK(MenuCategory.MAIN, "티본스테이크", 55000, false, true),
    BBQ_RIB(MenuCategory.MAIN, "바비큐립", 54000, false, true),
    SEAFOOD_PASTA(MenuCategory.MAIN, "해산물파스타", 35000, false, true),
    CHRISTMAS_PASTA(MenuCategory.MAIN, "크리스마스파스타", 25000, false, true),

    // 디저트
    CHOCO_CAKE(MenuCategory.DESSERT, "초코케이크", 15000, true, false),
    ICE_CREAM(MenuCategory.DESSERT, "아이스크림", 5000, true, false),

    // 음료
    ZERO_COLA(MenuCategory.BEVERAGE, "제로콜라", 3000, false, false),
    RED_WINE(MenuCategory.BEVERAGE, "레드와인", 60000, false, false),
    CHAMPAGNE(MenuCategory.BEVERAGE, "샴페인", 25000, false, false);

    private final MenuCategory category;
    private final String itemName;
    private final int price;
    private final boolean weekdayDiscount;
    private final boolean weekendDiscount;

    MenuItem(MenuCategory category, String itemName, int price, boolean weekdayDiscount, boolean weekendDiscount) {
        this.category = category;
        this.itemName = itemName;
        this.price = price;
        this.weekdayDiscount = weekdayDiscount;
        this.weekendDiscount = weekendDiscount;
    }

    public static boolean isItemNameInMenu(String orderItemsKey) {
        if (orderItemsKey == null) {
            return false;
        }
        for (MenuItem menuItem : values()) {
            if (menuItem.getItemName().equals(orderItemsKey)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkForInvalidOrders(String orderSheet) {
        for (MenuItem menuItem : MenuItem.values()) {
            if (findBeverageByName(orderSheet, menuItem)) {
                return false;
            }
        }
        return true;
    }

    private static boolean findBeverageByName(String orderSheet, MenuItem menuItem) {
        if (menuItem.getCategory() == MenuCategory.BEVERAGE) {
            return Objects.equals(menuItem.getItemName(), orderSheet);
        }
        return false;
    }

    public static int findProductPrice(String orderItem, Integer count) {
        int sumPrice = 0;
        for (MenuItem itemPrice : values()) {
            if (itemPrice.getItemName().equals(orderItem)) {
                sumPrice += itemPrice.getPrice() * count;
            }
        }
        return sumPrice;
    }

    public static int giveDayDiscount(String orderItem, int count, int day) {
        return Arrays.stream(values())
                .filter(itemPrice -> orderItem.equals(itemPrice.itemName))
                .filter(itemPrice -> {
                    DiscountDay discountDay = getDiscountDay(day);
                    return (discountDay == DiscountDay.WEEKEND && itemPrice.isWeekendDiscount()) ||
                            (discountDay == DiscountDay.WEEKDAY && itemPrice.isWeekdayDiscount());
                })
                .mapToInt(itemPrice -> 2023 * count)
                .sum();
    }

    private static DiscountDay getDiscountDay(int day) {
        for (DiscountDay discountDay : DiscountDay.values()) {
            if (discountDay.containsDay(day)) {
                return discountDay;
            }
        }
        return null;
    }

    public static String giveDay(int day) {
        for (DiscountDay discountDay : DiscountDay.values()) {
            if (discountDay.containsDay(day)) {
                return discountDay.getDescription();
            }
        }
        return null;
    }

    public static int giveSpecialDiscount(int day) {
        if (DiscountDay.SPECIAL_DAY.containsDay(day)) {
            return 1000;
        }
        return 0;
    }

    public boolean isWeekdayDiscount() {
        return weekdayDiscount;
    }

    public boolean isWeekendDiscount() {
        return weekendDiscount;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }
}
