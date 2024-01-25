package hello.spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hello.spring.domain.Member;
import hello.spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    private MemberService memberService;
    private MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @DisplayName("회원 가입을 한 이름을 성공적으로 가져올 수 있다.")
    @Test
    void 회원_가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long savedId = memberService.join(member);

        // then
        Member foundMember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @DisplayName("회원 가입 시 중복된 회원이 있을 때 예외를 발생한다.")
    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // then
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");

    }

}
