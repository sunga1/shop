package sun0aaa.s.shop.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sun0aaa.s.shop.Entity.Member;
import sun0aaa.s.shop.repository.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //세션 유지 시간 = 3600초
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);

        Member loginMember = memberRepository.findByEmail(authentication.getName()).get();

        //성공 시 메세지 출력 후 홈 화면으로 redirect
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if(prevPage!=null){
            pw.println("<script>alert('"+loginMember.getName()+"님 반갑습니다!'); location.href='" + prevPage + "';</script>");
        } else{
            pw.println("<script>alert('" + loginMember.getName() + "님 반갑습니다!'); location.href='/';</script>");
        }

        pw.flush();
    }
}
