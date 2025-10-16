package org.example.tackit.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DeletedMemberResp {
    private long totalCount;
    private List<DeletedMemberDTO> members;

    public DeletedMemberResp(List<DeletedMemberDTO> members, long totalCount) {
        this.members = members;
        this.totalCount = totalCount;
    }
}
