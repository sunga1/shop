package sun0aaa.s.shop.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.repository.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private final MemberRepository memberRepository;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member loginMember = null;
        if(auth !=null){
            loginMember = memberRepository.findByEmail(auth.getName()).get();
        }
        String requestURI = request.getRequestURI();

        //로그인한 유저가 login, join을 시도한 경우
        if(requestURI.contains("/members/login")||requestURI.contains("/members/join")){
            //메세지 출력 후 홈으로 redirect
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('이미 로그인 되어있습니다!'); location.href='/';</script>");
            pw.flush();
        }

        //ADMIN이 아닌데 관리자 페이지에 접속한 경우
        else if(requestURI.contains("admin")){
            //메세지 출력 후 홈으로 redirect
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('관리자만 접속 가능합니다!'); location.href='/';</script>");
            pw.flush();
        }
    }
}
