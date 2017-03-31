package com.mckesson.eig.roi.journal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.journal.model.EntityDTO;
import com.mckesson.eig.roi.journal.model.FinancialLineItemType;
import com.mckesson.eig.roi.journal.model.JournalDetail;
import com.mckesson.eig.roi.journal.model.JournalEntry;
import com.mckesson.eig.roi.journal.model.JournalTemplate;
import com.mckesson.eig.roi.journal.model.JournalTemplateDTO;
import com.mckesson.eig.roi.journal.model.LedgerAccountCategoryDTO;
import com.mckesson.eig.roi.journal.model.LedgerAccountDTO;
import com.mckesson.eig.roi.journal.model.LedgerAccountTypeDTO;
import com.mckesson.eig.roi.journal.model.LineItemDTO;
import com.mckesson.eig.roi.journal.model.TransactionTypeDTO;
import com.mckesson.eig.roi.test.BaseROITestCase;

public class TestROIJournalService extends BaseROITestCase {

    protected static final String JOURNAL_SERVICE = "com.mckesson.eig.roi.journal.service.JournalServiceImpl";

    private static JournalService _journalService;
    private static String INCOME_CODE = "income";
    private static final int ENTITY_ID = 100;
    private static String PYMNT_APPLIED_CODE = "APPLIED_PAYMENT";
    private static String PYMNT_APPLIED_NAME = "Applied payment to invoice";
    private static String PYMNT_APPLIED_QUERY = "select_applied_payment_amount";
    private static Date date = new Date();
    private static final double AMOUNT = 100.00;

    @Override
    public void initializeTestData() throws Exception {
        setUp();
        _journalService = (JournalService) getService(JOURNAL_SERVICE);
        insertDataSet("test/resources/reports/reportsDataSet.xml");
    }

    public void testcreateSendInvoiceJE(){
        boolean b = _journalService.createAcceptPaymentJE(1);
        assertTrue(b);
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException(
                "TestROIJournalService.getServiceURL()");
    }

    public void testLedgerAccountCategoryDTOModel() {

        LedgerAccountCategoryDTO legerAccCatDTO = new LedgerAccountCategoryDTO();
        List<LedgerAccountDTO> ledgerAccounts = new ArrayList<LedgerAccountDTO>();
        LedgerAccountDTO accDTO = new LedgerAccountDTO();

        legerAccCatDTO.setId(0);
        legerAccCatDTO.set_name("Test");

        accDTO.setBalance(100);
        accDTO.setId(0);
        accDTO.setName("Test");
        accDTO.setLiquidityOrder(0);
        accDTO.setCode("Test");

        ledgerAccounts.add(accDTO);

        legerAccCatDTO.setLedgerAccounts(ledgerAccounts);

        assertEquals(0, legerAccCatDTO.getId());
        assertEquals("Test", legerAccCatDTO.getName());
        assertNotNull(legerAccCatDTO.getLedgerAccounts());

        assertEquals(100, accDTO.getBalance());
        assertEquals(0, accDTO.getLiquidityOrder());
        assertEquals(0, accDTO.getId());
        assertEquals("Test", accDTO.getName());
        assertEquals("Test", accDTO.getCode());

    }

    public void testEntityDTOModel() {

        EntityDTO entDTO = new EntityDTO();

        entDTO.setCode("Test");
        entDTO.setDescription("Test");
        entDTO.setName("Test");

        LineItemDTO lineItemDTO = new LineItemDTO();
        lineItemDTO.setName("Test");
        lineItemDTO.setQuery("Test");

        assertEquals("Test", lineItemDTO.getName());
        assertEquals("Test", lineItemDTO.getQuery());

        List<LineItemDTO> lineItems = new ArrayList<LineItemDTO>();
        lineItems.add(lineItemDTO);
        entDTO.set_lineItems(lineItems);
        assertEquals("Test", entDTO.getCode());
        assertEquals("Test", entDTO.getDescription());
        assertEquals("Test", entDTO.getName());
        assertNotNull(entDTO.getLineItems());

    }

    public void testJournalTemplateDTOModel() {

        JournalTemplateDTO jrnlTempDTO = new JournalTemplateDTO();

        jrnlTempDTO.setCreditDebitFl("Sample");
        jrnlTempDTO.setIncDecFl("Test");
        jrnlTempDTO.setLedgerAccount(0);
        jrnlTempDTO.setLineItemType(0);

        assertEquals("Sample", jrnlTempDTO.getCreditDebitFl());
        assertEquals("Test", jrnlTempDTO.getIncDecFl());
        assertEquals(0, jrnlTempDTO.getLedgerAccount());
        assertEquals(0, jrnlTempDTO.getLineItemType());

    }

    public void testTransactionTypeDTOModel() {

        TransactionTypeDTO transDTO = new TransactionTypeDTO();
        transDTO.setCode("Sample");
        transDTO.setDescription("Sample");
        List<EntityDTO>  entities = new ArrayList<EntityDTO>();
        transDTO.setEntities(entities);
        List<JournalTemplateDTO> journalTemplates = new ArrayList<JournalTemplateDTO>();
        transDTO.setJournalTemplates(journalTemplates);

        assertEquals("Sample", transDTO.getCode());
        assertEquals("Sample", transDTO.getDescription());
        assertNotNull(transDTO.getEntities());
        assertNotNull(transDTO.getJournalTemplates());

    }

    public void testJournalEntry() {

        JournalEntry jrnlEnty = new JournalEntry();

        jrnlEnty.setJournalEntryId(0);
        jrnlEnty.setTransactionEventId(0);
        jrnlEnty.setRequestorId(0);

        assertEquals(0, jrnlEnty.getJournalEntryId());
        assertEquals(0, jrnlEnty.getTransactionEventId());
        assertEquals(0, jrnlEnty.getRequestorId());

    }
    
    public void testLedgerAccountTypeDTO() {
        
        try {        
            LedgerAccountTypeDTO dto = new LedgerAccountTypeDTO();
            dto.setCode(INCOME_CODE);
            dto.setId(ENTITY_ID);
            dto.setIncStmtBalSheetFl(true);
            dto.setName(INCOME_CODE);
            dto.setNormalBalFl(false);
            
            assertEquals(INCOME_CODE, dto.getCode());
            assertEquals(ENTITY_ID, dto.getId());
            assertEquals(INCOME_CODE, dto.getName());
            assertTrue(dto.isIncStmtBalSheetFl());
            assertFalse(dto.isNormalBalFl());
        } catch (Exception e) {
            fail("LedgerAccountTypeDTO should not thrown exception. " + e.getMessage());            
        }
    }
    
public void testFinancialLineItemType() {
        
        try {        
            FinancialLineItemType dto = new FinancialLineItemType();
            dto.setCode(PYMNT_APPLIED_CODE);
            dto.setCreatedBy(1);
            dto.setCreatedDt(date);
            dto.setEntityId(ENTITY_ID);
            dto.setId(ENTITY_ID);
            dto.setModifiedBy(1);
            dto.setModifiedDt(date);
            dto.setName(PYMNT_APPLIED_NAME);
            dto.setQuery(PYMNT_APPLIED_QUERY);
            dto.setRecordVersion(1);
            dto.setTxnTypeId(ENTITY_ID);
            
            assertEquals(PYMNT_APPLIED_CODE, dto.getCode());
            assertEquals(1, dto.getCreatedBy());
            assertEquals(date, dto.getCreatedDt());
            assertEquals(ENTITY_ID, dto.getEntityId());
            assertEquals(ENTITY_ID, dto.getId());
            assertEquals(1, dto.getModifiedBy());
            assertEquals(date, dto.getModifiedDt());
            assertEquals(PYMNT_APPLIED_NAME, dto.getName());
            assertEquals(PYMNT_APPLIED_QUERY, dto.getQuery());
            assertEquals(1, dto.getRecordVersion());
            assertEquals(ENTITY_ID, dto.getTxnTypeId());
            
        } catch (Exception e) {
            fail("FinancialLineItemType should not thrown exception. " + e.getMessage());
            
        }
    }
    
    public void testTransactionTypeDTO() {
        
        try {        
            TransactionTypeDTO dto = new TransactionTypeDTO();
            dto.setCode(PYMNT_APPLIED_CODE);
            dto.setDescription(PYMNT_APPLIED_NAME);
            dto.setName(PYMNT_APPLIED_NAME);
            dto.setEntities(new ArrayList<EntityDTO>());
            dto.setJournalTemplates(new ArrayList<JournalTemplateDTO>());
            
            
            assertEquals(PYMNT_APPLIED_CODE, dto.getCode());
            assertEquals(PYMNT_APPLIED_NAME, dto.getName());
            assertEquals(PYMNT_APPLIED_NAME, dto.getDescription());
            assertNotNull(dto.getEntities());
            assertNotNull(dto.getJournalTemplates());
            
        } catch (Exception e) {
            fail("FinancialLineItemType should not thrown exception. " + e.getMessage());
            
        }
    }
    
    public void testJournalDetail() {
        
        try {        
            JournalDetail dto = new JournalDetail();
            dto.setInvoiceId(ENTITY_ID);
            JournalDetail.createByInvoiceId(new JournalTemplate(), date, AMOUNT, ENTITY_ID, ENTITY_ID);            
            
            assertEquals(ENTITY_ID, dto.getInvoiceId());
            
        } catch (Exception e) {
            fail("FinancialLineItemType should not thrown exception. " + e.getMessage());
            
        }
    }
    
    public void testJournalTemplate() {
        
        try {        
            JournalTemplate dto = new JournalTemplate();
            dto.setActive(true);
            dto.setId(ENTITY_ID);
            dto.setTxnTypeId(ENTITY_ID);
            
            assertTrue(dto.isActive());
            assertEquals(ENTITY_ID, dto.getId());
            assertEquals(ENTITY_ID, dto.getTxnTypeId());
            
        } catch (Exception e) {
            fail("FinancialLineItemType should not thrown exception. " + e.getMessage());
            
        }
    }
    
    public void testCreateAcceptPaymentJE() {
        boolean isCreated = _journalService.createAcceptPaymentJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateApplyAdjustmentToInvoiceJE() {
        boolean isCreated = _journalService.createApplyAdjustmentToInvoiceJE(1001);
        assertTrue(isCreated);
    }
    
    public void testCreateApplyBadDebtAdjustmentToInvoiceJE() {
        boolean isCreated = _journalService.createApplyBadDebtAdjustmentToInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateApplyGoodWillAdjustmentToInvoiceJE() {
        boolean isCreated = _journalService.createApplyGoodWillAdjustmentToInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateApplyPaymentToInvoiceJE() {
        boolean isCreated = _journalService.createApplyPaymentToInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateCreateAdjustmentJE() {
        boolean isCreated = _journalService.createCreateAdjustmentJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateIssueRefundJE() {
        boolean isCreated = _journalService.createIssueRefundJE(1001);
        assertTrue(isCreated);
    }
    
    public void testCreateRecordBadDebtJE() {
        boolean isCreated = _journalService.createRecordBadDebtJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateRecordCustomerGoodwillJE() {
        boolean isCreated = _journalService.createRecordCustomerGoodwillJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateRemoveAdjustmentJE() {
        boolean isCreated = _journalService.createRemoveAdjustmentJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateRemoveBadDebtJE() {
        boolean isCreated = _journalService.createRemoveBadDebtJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateRemoveCustomerGoodwillJE() {
        boolean isCreated = _journalService.createRemoveCustomerGoodwillJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateUnapplyAdjustmentFromInvoiceJE() {
        boolean isCreated = _journalService.createUnapplyAdjustmentFromInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateUnapplyBadDebtAdjustmentFromInvoiceJE() {
        boolean isCreated = _journalService.createUnapplyBadDebtAdjustmentFromInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateUnapplyGoodWillAdjustmentFromInvoiceJE() {
        boolean isCreated = _journalService.createUnapplyGoodWillAdjustmentFromInvoiceJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateVoidPaymentJE() {
        boolean isCreated = _journalService.createVoidPaymentJE(1002);
        assertTrue(isCreated);
    }
    
    public void testCreateAcceptPaymentJEWithInvalidInput() {
        boolean isCreated = _journalService.createAcceptPaymentJE(0);
        assertTrue(isCreated);
    }
    
}
