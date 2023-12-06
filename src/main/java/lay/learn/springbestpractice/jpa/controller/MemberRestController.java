package lay.learn.springbestpractice.jpa.controller;

import jakarta.validation.Valid;
import lay.learn.springbestpractice.jpa.entity.Member;
import lay.learn.springbestpractice.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/api/v1/member")
    public List<Member> membersV1() {
        return memberService.findAll();
    }

    @GetMapping("/api/v2/members")
    public List<MemberDto> membersV2(){
        List<Member> members = memberService.findAll();
        return members.stream()
                .map(member -> new MemberDto(member.getName()))
                .toList();
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Member savedMember = memberService.save(member);
        return new CreateMemberResponse(savedMember.getId());
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = Member.builder()
                .name(request.name())
                .build();

        Member savedMember = memberService.save(member);
        return new CreateMemberResponse(savedMember.getId());
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.name());
        Member findMember = memberService.findById(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    static record CreateMemberRequest(
             String name
    ){}

    record CreateMemberResponse(
       Long id
    ){}

    record UpdateMemberRequest(
            String name
    ){}

    record UpdateMemberResponse(
            Long id,
            String name
    ){}

    record MemberDto(
            String name
    ){}
}
