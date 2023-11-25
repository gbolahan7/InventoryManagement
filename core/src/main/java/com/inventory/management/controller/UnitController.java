package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.UnitService;
import com.inventory.management.vo.dto.UnitAuditDto;
import com.inventory.management.vo.dto.UnitDto;
import com.inventory.management.vo.dto.UnitRequestDto;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/unit")
@ResponseWrapper
public class UnitController {

    private final UnitService unitService;

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping
    public ResponseEntity<Page<UnitDto>> getCategories(PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> unitFilter) {
        return ResponseEntity.ok(unitService.getUnits(pageRequest, unitFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getUnit(@PathVariable("id") Long id) {
        return ResponseEntity.ok(unitService.getUnit(id));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping("/audit/id/{id}/revision/{revisionId}")
    public ResponseEntity<UnitAuditDto> getUnitAudit(@PathVariable Long id, @PathVariable Integer revisionId) {
        return ResponseEntity.ok(unitService.getUnitAudit(id, revisionId));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping("/audit/id/{id}")
    public ResponseEntity<Page<UnitAuditDto>> getUnitAudits(@PathVariable Long id, PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> unitAuditsFilter) {
        return ResponseEntity.ok(unitService.getUnitAudits(id, pageRequest, unitAuditsFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping("/request")
    public ResponseEntity<Page<UnitRequestDto>> getUnitRequests( PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> unitRequestFilter) {
        return ResponseEntity.ok(unitService.getUnitRequests(pageRequest, unitRequestFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_VIEW})
    @GetMapping("/request/{requestId}")
    public ResponseEntity<UnitRequestDto> getUnitRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(unitService.getUnitRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_CREATE})
    @PostMapping("/request/create")
    public ResponseEntity<Long> createUnitRequest(@RequestBody UnitRequestDto unitRequestBody) {
        unitRequestBody.setRequestType("create");
        return postUnitRequest(unitRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_MODIFY})
    @PostMapping("/request/modify")
    public ResponseEntity<Long> modifyUnitRequest(@RequestBody UnitRequestDto unitRequestBody) {
        unitRequestBody.setRequestType("modify");
        return postUnitRequest(unitRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_ACCESS})
    @GetMapping("/request/approve/{requestId}")
    public ResponseEntity<UnitRequestDto> approveUnitRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(unitService.approveUnitRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_DELETE})
    @GetMapping("/delete/{id}")
    public ResponseEntity<UnitDto> deleteUnit(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.deleteUnit(id));
    }

    @RolesAllowed({Privilege.INVENTORY_UNIT_ACCESS})
    @GetMapping("/request/reject/{requestId}")
    public ResponseEntity<UnitRequestDto> rejectUnitRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(unitService.rejectUnitRequest(requestId));
    }

    private ResponseEntity<Long> postUnitRequest(UnitRequestDto unitRequestBody) {
        long id = unitService.postUnitRequest(unitRequestBody);
        return ResponseEntity.created(URI.create("/" + id)).body(id);
    }

}
