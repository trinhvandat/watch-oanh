import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment-transaction.reducer';

export const PaymentTransactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paymentTransactionEntity = useAppSelector(state => state.paymentTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentTransactionDetailsHeading">Payment Transaction</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Translation missing for global.field.id</span>
          </dt>
          <dd>{paymentTransactionEntity.id}</dd>
          <dt>
            <span id="bankId">Bank Id</span>
          </dt>
          <dd>{paymentTransactionEntity.bankId}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{paymentTransactionEntity.amount}</dd>
          <dt>Order</dt>
          <dd>{paymentTransactionEntity.order ? paymentTransactionEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Translation missing for entity.action.back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment-transaction/${paymentTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Translation missing for entity.action.edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentTransactionDetail;
