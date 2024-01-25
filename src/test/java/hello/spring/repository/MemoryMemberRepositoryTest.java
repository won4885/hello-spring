package hello.spring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hello.spring.domain.Member;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    private MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @DisplayName("저장한 멤버와 가져온 멤버가 같다.")
    @Test
    void save() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        // then
        assertThat(member).isEqualTo(result);
    }

    @DisplayName("저장한 멤버의 Name을 가져올 수 있다.")
    @Test
    void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring1").get();

        // then
        assertThat(result).isEqualTo(member1);
    }

    @DisplayName("저장한 멤버 전체를 가져올 수 있다.")
    @Test
    void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

}
