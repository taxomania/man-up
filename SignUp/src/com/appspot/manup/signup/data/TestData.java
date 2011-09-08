package com.appspot.manup.signup.data;

import com.appspot.manup.signup.data.DataManager.Member;

import android.content.ContentValues;

/*
 * Auto generated by generate_test_data.py
 */

final class TestData
{

    private static final ContentValues sMember0;
    private static final ContentValues sMember1;
    private static final ContentValues sMember2;
    private static final ContentValues sMember3;
    private static final ContentValues sMember4;
    private static final ContentValues sMember5;
    private static final ContentValues sMember6;

    private static final ContentValues[] sMembers;

    static
    {
        sMember0 = new ContentValues();
        sMember0.put(Member.PERSON_ID, "7301496");
        sMember0.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember0.put(Member.LATEST_PENDING_SIGNATURE_REQUEST, "1315400102");
        sMember0.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember0.put(Member.GIVEN_NAME, "Peter");
        sMember0.put(Member.SURNAME, "Sutton");
        sMember0.put(Member.SIGNATURE_STATE, "captured");

        sMember1 = new ContentValues();
        sMember1.put(Member.PERSON_ID, "7145461");
        sMember1.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember1.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember1.put(Member.GIVEN_NAME, "Lloyd");
        sMember1.put(Member.SURNAME, "Henning");
        sMember1.put(Member.SIGNATURE_STATE, "captured");

        sMember2 = new ContentValues();
        sMember2.put(Member.PERSON_ID, "7178899");
        sMember2.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember2.put(Member.LATEST_PENDING_SIGNATURE_REQUEST, "1315400110");
        sMember2.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember2.put(Member.GIVEN_NAME, "Jack");
        sMember2.put(Member.SURNAME, "Youd");
        sMember2.put(Member.SIGNATURE_STATE, "uncaptured");

        sMember3 = new ContentValues();
        sMember3.put(Member.PERSON_ID, "7588142");
        sMember3.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember3.put(Member.LATEST_PENDING_SIGNATURE_REQUEST, "1315400120");
        sMember3.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember3.put(Member.GIVEN_NAME, "Lucy");
        sMember3.put(Member.SURNAME, "Mckenna");
        sMember3.put(Member.SIGNATURE_STATE, "uncaptured");

        sMember4 = new ContentValues();
        sMember4.put(Member.PERSON_ID, "7102497");
        sMember4.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember4.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember4.put(Member.GIVEN_NAME, "Steve");
        sMember4.put(Member.SURNAME, "Woby");
        sMember4.put(Member.SIGNATURE_STATE, "uploaded");

        sMember5 = new ContentValues();
        sMember5.put(Member.PERSON_ID, "7375725");
        sMember5.put(Member.PERSON_ID_VALIDATED, "valid");
        sMember5.put(Member.EXTRA_INFO_STATE, "retrieved");
        sMember5.put(Member.GIVEN_NAME, "Lilly");
        sMember5.put(Member.SURNAME, "Sommer");
        sMember5.put(Member.SIGNATURE_STATE, "uploaded");

        sMember6 = new ContentValues();
        sMember6.put(Member.PERSON_ID, "7374060");
        sMember6.put(Member.PERSON_ID_VALIDATED, "unknown");
        sMember6.put(Member.LATEST_PENDING_SIGNATURE_REQUEST, "1315400101");
        sMember6.put(Member.EXTRA_INFO_STATE, "none");
        sMember6.put(Member.SIGNATURE_STATE, "uncaptured");

        sMembers = new ContentValues[] {
                sMember0,
                sMember1,
                sMember2,
                sMember3,
                sMember4,
                sMember5,
                sMember6,
        };

    } // static

    private static ContentValues getMember(final int index)
    {
        return new ContentValues(sMembers[index]);
    } // getMember(int)

    public static ContentValues[] getMembers()
    {
        return getMembers(0, sMembers.length);
    } // getMembers()

    public static ContentValues[] getMembers(final int startIndex, final int endIndex)
    {
        final ContentValues[] copies = new ContentValues[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; i++)
        {
            copies[i] = getMember(i);
        }
        return copies;
    } // getMembers(int, int)

} // class TestData
