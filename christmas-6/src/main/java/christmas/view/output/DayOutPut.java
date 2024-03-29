package christmas.view.output;

public class DayOutPut extends OutPut {

    private static final String INFORMATION_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String VISIT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    @Override
    public void showInformation() {
        System.out.println(INFORMATION_MESSAGE + NEW_LINE + VISIT_DATE);
    }
}
