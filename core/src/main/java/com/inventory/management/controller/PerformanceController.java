package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.repository.StaffPerformanceRepository;
import com.inventory.management.service.PerformanceSettingService;
import com.inventory.management.service.StaffPerformanceService;
import com.inventory.management.vo.dto.PerformanceSettingAuditDto;
import com.inventory.management.vo.dto.PerformanceSettingDto;
import com.inventory.management.vo.dto.PerformanceSettingRequestDto;
import com.inventory.management.vo.dto.StaffPerformanceDto;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/performance")
@ResponseWrapper
public class PerformanceController {

    private final PerformanceSettingService performanceSettingService;
    private final StaffPerformanceService staffPerformanceService;
    

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_VIEW})
    @GetMapping("/setting/{id}")
    public ResponseEntity<PerformanceSettingDto> getPerformanceSetting(@PathVariable("id") Long id) {
        return ResponseEntity.ok(performanceSettingService.getPerformanceSetting(id));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_VIEW})
    @GetMapping("/setting/audit/id/{id}/revision/{revisionId}")
    public ResponseEntity<PerformanceSettingAuditDto> getPerformanceSettingAudit(@PathVariable Long id, @PathVariable Integer revisionId) {
        return ResponseEntity.ok(performanceSettingService.getPerformanceSettingAudit(id, revisionId));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_VIEW})
    @GetMapping("/setting/audit/id/{id}")
    public ResponseEntity<Page<PerformanceSettingAuditDto>> getPerformanceSettingAudits(@PathVariable Long id, PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> performanceSettingAuditsFilter) {
        return ResponseEntity.ok(performanceSettingService.getPerformanceSettingAudits(id, pageRequest, performanceSettingAuditsFilter));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_VIEW})
    @GetMapping("/setting/request")
    public ResponseEntity<Page<PerformanceSettingRequestDto>> getPerformanceSettingRequests( PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> performanceSettingRequestFilter) {
        return ResponseEntity.ok(performanceSettingService.getPerformanceSettingRequests(pageRequest, performanceSettingRequestFilter));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_VIEW})
    @GetMapping("/setting/request/{requestId}")
    public ResponseEntity<PerformanceSettingRequestDto> getPerformanceSettingRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(performanceSettingService.getPerformanceSettingRequest(requestId));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_MODIFY})
    @PostMapping("/setting/request/modify")
    public ResponseEntity<Long> modifyPerformanceSettingRequest(@RequestBody PerformanceSettingRequestDto performanceSettingRequestBody) {
        performanceSettingRequestBody.setRequestType("modify");
        return postPerformanceSettingRequest(performanceSettingRequestBody);
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_ACCESS})
    @GetMapping("/setting/request/approve/{requestId}")
    public ResponseEntity<PerformanceSettingRequestDto> approvePerformanceSettingRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(performanceSettingService.approvePerformanceSettingRequest(requestId));
    }

    @RolesAllowed({Privilege.PERFORMANCE_SETTING_ACCESS})
    @GetMapping("/setting/request/reject/{requestId}")
    public ResponseEntity<PerformanceSettingRequestDto> rejectPerformanceSettingRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(performanceSettingService.rejectPerformanceSettingRequest(requestId));
    }

    @RolesAllowed({Privilege.PERFORMANCE_STAFF_VIEW})
    @GetMapping("/staff")
    public ResponseEntity<List<StaffPerformanceDto>> getStaffPerformance() {
        return ResponseEntity.ok(staffPerformanceService.getAllStaffPerformance());
    }

    private ResponseEntity<Long> postPerformanceSettingRequest(PerformanceSettingRequestDto performanceSettingRequestBody) {
        long id = performanceSettingService.postPerformanceSettingRequest(performanceSettingRequestBody);
        return ResponseEntity.created(URI.create("/" + id)).body(id);
    }

}
