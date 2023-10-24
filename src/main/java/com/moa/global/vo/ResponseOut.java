// 패키지 선언: 현재 클래스는 'com.moa.global.vo' 패키지에 위치합니다.
package com.moa.global.vo;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import lombok.Getter;
import lombok.Setter;

// @Getter와 @Setter 어노테이션: Lombok 라이브러리를 사용하여 getter와 setter 메서드를 자동으로 생성합니다.
@Getter
@Setter
public class ResponseOut<T> {
    // 응답의 성공 여부를 나타내는 필드입니다.
    private boolean success;
    // 응답 코드를 나타내는 필드입니다.
    private int code;
    // 응답에 포함될 결과 데이터를 나타내는 제네릭 필드입니다.
    private T result;

    // 성공 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> success() {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(true);
        re.setCode(1000);
        re.setResult(null);
        return re;
    }

    // 실패 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> fail() {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(false);
        re.setCode(1000);
        re.setResult(null);
        return re;
    }

    // 주어진 결과 데이터를 포함하는 성공 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> success(T result) {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(true);
        re.setCode(1000);
        re.setResult(result);
        return re;
    }

    // 주어진 결과 데이터를 포함하는 실패 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> fail(T result) {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(false);
        re.setCode(1000);
        re.setResult(result);
        return re;
    }

    // 로그인 ID 검사 결과를 포함하는 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> checkLogId(T result) {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(false);
        re.setCode(3100);
        re.setResult(result);
        return re;
    }

    // 토큰 에러를 나타내는 응답 객체를 반환하는 정적 메서드입니다.
    public static <T> ResponseOut<T> tokenInvalid() {
        ResponseOut<T> re = new ResponseOut<>();
        re.setSuccess(false);
        re.setCode(3200);
        return re;
    }
}

