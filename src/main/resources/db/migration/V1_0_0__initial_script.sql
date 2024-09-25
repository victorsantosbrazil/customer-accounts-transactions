/*
 * This script creates the initial tables for the customers accounts and transactions.
 * Creation date: 2024-09-24
 */

-- Create tables
CREATE TABLE account (
    id bigint GENERATED ALWAYS AS IDENTITY,
    document_number varchar(255) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT account_document_number_key UNIQUE (document_number)
);

CREATE TABLE "transaction" (
    id bigint GENERATED ALWAYS AS IDENTITY,
    amount numeric(38, 2) NOT NULL,
    operation_type_id smallint NOT NULL,
    account_id bigint NOT NULL,
    event_date timestamp(6) NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(id)
);

