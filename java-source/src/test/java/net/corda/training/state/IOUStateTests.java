package net.corda.training.state;

import net.corda.core.contracts.*;
import net.corda.core.identity.Party;
import net.corda.finance.*;

import static net.corda.training.TestUtils.*;

import java.lang.reflect.Field;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;


/**
 * Practical exercise instructions.
 * Uncomment the first unit test [hasIOUAmountFieldOfCorrectType] then run the unit test using the green arrow
 * to the left of the [IOUStateTests] class or the [hasIOUAmountFieldOfCorrectType] method.
 * Running the unit tests from [IOUStateTests] runs all of the unit tests defined in the class.
 * The test should fail because you need to make some changes to the IOUState to make the test pass. Read the TODO
 * under each task number for a description and a hint of what you need to do.
 * Once you have the unit test passing, uncomment the next test.
 * Continue until all the unit tests pass.
 * Hint: CMD / Ctrl + click on the brown type names in square brackets for that type's definition in the codebase.
 */
public class IOUStateTests {

    /**
     * Task 1.
     * TODO: Add an 'amount' property of type [Amount] to the [IOUState] class to get this test to pass.
     * Hint: [Amount] is a template class that takes a class parameter of the token you would like an [Amount] of.
     * As we are dealing with cash lent from one Party to another a sensible token to use would be [Currency].
     */
    @Test
    public void hasIOUAmountFieldOfCorrectType() throws NoSuchFieldException {
        // Does the amount field exist?
        Field amountField = IOUState.class.getDeclaredField("amount");
        // Is the amount field of the correct type?
        assertTrue(amountField.getType().isAssignableFrom(Amount.class));
    }

    /**
     * Task 2.
     * TODO: Add a 'lender' property of type [Party] to the [IOUState] class to get this test to pass.
     */
    @Test
    public void hasLenderFieldOfCorrectType() throws NoSuchFieldException {
        // Does the lender field exist?
        Field lenderField = IOUState.class.getDeclaredField("lender");
        // Is the lender field of the correct type?
        assertTrue(lenderField.getType().isAssignableFrom(Party.class));
    }

    /**
     * Task 3.
     * TODO: Add a 'borrower' property of type [Party] to the [IOUState] class to get this test to pass.
     */
    @Test
    public void hasBorrowerFieldOfCorrectType() throws NoSuchFieldException {
        // Does the borrower field exist?
        Field borrowerField = IOUState.class.getDeclaredField("borrower");
        // Is the borrower field of the correct type?
        assertTrue(borrowerField.getType().isAssignableFrom(Party.class));
    }

    /**
     * Task 4.
     * TODO: Add a 'paid' property of type [Amount] to the [IOUState] class to get this test to pass.
     * Hint:
     * - We would like this property to be initialised to a zero amount of Currency upon creation of the [IOUState].
     * - You can use the [POUNDS] function from Currencies to create an amount of pounds e.g. 'Currencies.POUNDS(10)'.
     * - This property keeps track of how much of the initial [IOUState.amount] has been settled by the borrower
     *
     * - We need to make sure that the [IOUState.paid] property is of the same currency type as the
     *   [IOUState.amount] property. You can create an instance of the [Amount] class that takes a zero value and a token
     *   representing the currency - which should be the same currency as the [IOUState.amount] property.
     */
    @Test
    public void hasPaidFieldOfCorrectType() throws NoSuchFieldException {
        // Does the paid field exist?
        Field paidField = IOUState.class.getDeclaredField("paid");
        // Is the paid field of the correct type?
        assertTrue(paidField.getType().isAssignableFrom(Amount.class));
    }

    /**
     * Task 5.
     * TODO: Include the lender within the [IOUState.participants] list
     * Hint: [ImmutableList.of] takes any number of parameters and will add them to the list
     */
    @Test
    public void lenderIsParticipant() {
        IOUState iouState = new IOUState(Currencies.POUNDS(0), ALICE.getParty(), BOB.getParty());
        assertNotEquals(iouState.getParticipants().indexOf(ALICE.getParty()), -1);
    }

    /**
     * Task 6.
     * TODO: Similar to the last task, include the borrower within the [IOUState.participants] list
     */
    @Test
    public void borrowerIsParticipant() {
        IOUState iouState = new IOUState(Currencies.POUNDS(0), ALICE.getParty(), BOB.getParty());
        assertNotEquals(iouState.getParticipants().indexOf(BOB.getParty()), -1);
    }

    /**
     * Task 7.
     * TODO: Implement [LinearState] along with the required methods.
     * Hint: [LinearState] implements [ContractState] which defines an additional method. You can use
     * IntellIJ to automatically add the member definitions for you or you can add them yourself. Look at the definition
     * of [LinearState] for what requires adding.
     */
    @Test
    public void isLinearState() {
        assert(LinearState.class.isAssignableFrom(IOUState.class));
    }

    /**
     * Task 8.
     * TODO: Override the [LinearState.getLinearId] method and assign it a value via your state's constructor.
     * Hint:
     * - The [linearId] property is of type [UniqueIdentifier]. You need to create a new instance of
     * the [UniqueIdentifier] class.
     * - The [linearId] is designed to link all [LinearState]s (which represent the state of an
     * agreement at a specific point in time) together. All the [LinearState]s with the same [linearId]
     * represent the complete life-cycle to date of an agreement, asset or shared fact. 
     * - Provide a new constructor that creates an [IOUState] with a new [linearId], as opposed to copying an existing one.
     * - Note: With two constructors, it must be specified which one is to be used by the serialization engine to generate the class schema.
     * To accomplish this, add an @ConstructorForDeserialization annotation to the default constructor.
     */
    @Test
    public void hasLinearIdFieldOfCorrectType() throws NoSuchFieldException {
        // Does the linearId field exist?
        Field linearIdField = IOUState.class.getDeclaredField("linearId");

        // Is the linearId field of the correct type?
        assertTrue(linearIdField.getType().isAssignableFrom(UniqueIdentifier.class));
    }

    /**
     * Task 9.
     * TODO: Ensure parameters are ordered correctly.
     * Hint: Make sure that the lender and borrower fields are not in the wrong order as this may cause some
     * confusion in subsequent tasks!
     */
    @Test
    public void checkIOUStateParameterOrdering() throws NoSuchFieldException {
        
        List<Field> fields = Arrays.asList(IOUState.class.getDeclaredFields());
  
        int amountIdx = fields.indexOf(IOUState.class.getDeclaredField("amount"));
        int lenderIdx = fields.indexOf(IOUState.class.getDeclaredField("lender"));
        int borrowerIdx = fields.indexOf(IOUState.class.getDeclaredField("borrower"));
        int paidIdx = fields.indexOf(IOUState.class.getDeclaredField("paid"));
        int linearIdIdx = fields.indexOf(IOUState.class.getDeclaredField("linearId"));

        assertTrue(amountIdx < lenderIdx);
        assertTrue(lenderIdx < borrowerIdx);
        assertTrue(borrowerIdx < paidIdx);
        assertTrue(paidIdx < linearIdIdx);
    }

    /**
     * Task 10.
     * TODO: Add a helper method called [pay] that can be called from an [IOUState] to settle an amount of the IOU.
     * Hint:
     * - You will need to increase the [IOUState.paid] property by the amount the borrower wishes to pay.
     * - Add a new function called [pay] in [IOUState]. This function will need to return an [IOUState].
     * - The existing state is immutable, so a new state must be created from the existing state. As this change represents an update 
     * in the lifecycle of an asset, it should share the same [linearId]. To enforce this distinction between updating vs creating a new state, make the default constructor
     * private, to be used as a copy constructor.  
     */
    @Test
    public void checkPayHelperMethod() {
        IOUState iou = new IOUState(Currencies.DOLLARS(10), ALICE.getParty(), BOB.getParty());
        assertEquals(Currencies.DOLLARS(5), iou.pay(Currencies.DOLLARS(5)).getPaid());
        assertEquals(Currencies.DOLLARS(3), iou.pay(Currencies.DOLLARS(1)).pay(Currencies.DOLLARS(2)).getPaid());
        assertEquals(Currencies.DOLLARS(10), iou.pay(Currencies.DOLLARS(5)).pay(Currencies.DOLLARS(3)).pay(Currencies.DOLLARS(2)).getPaid());
    }

    /**
     * Task 11.
     * TODO: Add a helper method called [withNewLender] that can be called from an [IOUState] to change the IOU's lender. This will also utilize the copy constructor.
     */
    @Test
    public void checkWithNewLenderHelperMethod() {
        IOUState iou = new IOUState(Currencies.DOLLARS(10), ALICE.getParty(), BOB.getParty());
        assertEquals(MINICORP.getParty(), iou.withNewLender(MINICORP.getParty()).getLender());
        assertEquals(MEGACORP.getParty(), iou.withNewLender(MEGACORP.getParty()).getLender());
    }
}
