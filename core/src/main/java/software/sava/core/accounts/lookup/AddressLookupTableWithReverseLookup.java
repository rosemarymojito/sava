package software.sava.core.accounts.lookup;

import software.sava.core.accounts.PublicKey;

import java.util.Arrays;
import java.util.Objects;

final class AddressLookupTableWithReverseLookup extends AddressLookupTableRoot {

  private final PublicKey[] accounts;
  private final AccountIndexLookupTableEntry[] reverseLookupTable;

  AddressLookupTableWithReverseLookup(final PublicKey address,
                                      final byte[] discriminator,
                                      final long deactivationSlot,
                                      final long lastExtendedSlot,
                                      final int lastExtendedSlotStartIndex,
                                      final PublicKey authority,
                                      final PublicKey[] accounts,
                                      final AccountIndexLookupTableEntry[] reverseLookupTable) {
    super(address, discriminator, deactivationSlot, lastExtendedSlot, lastExtendedSlotStartIndex, authority);
    this.accounts = accounts;
    this.reverseLookupTable = reverseLookupTable;
  }

  @Override
  public AddressLookupTable withReverseLookup() {
    return this;
  }

  @Override
  public PublicKey account(final int index) {
    return accounts[index];
  }

  @Override
  public int indexOf(final PublicKey publicKey) {
    return AccountIndexLookupTableEntry.lookupAccountIndex(this.reverseLookupTable, publicKey);
  }

  @Override
  public byte indexOfOrThrow(final PublicKey publicKey) {
    return AccountIndexLookupTableEntry.lookupAccountIndexOrThrow(this.reverseLookupTable, publicKey);
  }

  @Override
  public int numAccounts() {
    return accounts.length;
  }

  @Override
  protected String keysToString() {
    return Arrays.toString(accounts);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (AddressLookupTableWithReverseLookup) obj;
    return Objects.equals(this.address, that.address) &&
        Arrays.equals(this.discriminator, that.discriminator) &&
        this.deactivationSlot == that.deactivationSlot &&
        this.lastExtendedSlot == that.lastExtendedSlot &&
        this.lastExtendedSlotStartIndex == that.lastExtendedSlotStartIndex &&
        Objects.equals(this.authority, that.authority) &&
        Arrays.equals(this.accounts, that.accounts) &&
        Arrays.equals(this.reverseLookupTable, that.reverseLookupTable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, Arrays.hashCode(discriminator), deactivationSlot, lastExtendedSlot, lastExtendedSlotStartIndex, authority, Arrays.hashCode(accounts), Arrays.hashCode(reverseLookupTable));
  }
}
