import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './payment-transaction.reducer';

export const PaymentTransactionDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const paymentTransactionEntity = useAppSelector(state => state.paymentTransaction.entity);
  const updateSuccess = useAppSelector(state => state.paymentTransaction.updateSuccess);

  const handleClose = () => {
    navigate('/payment-transaction');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(paymentTransactionEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="paymentTransactionDeleteDialogHeading">
        Translation missing for entity.delete.title
      </ModalHeader>
      <ModalBody id="watchSericeApp.paymentTransaction.delete.question">
        Are you sure you want to delete Payment Transaction {paymentTransactionEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Translation missing for entity.action.cancel
        </Button>
        <Button id="jhi-confirm-delete-paymentTransaction" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Translation missing for entity.action.delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default PaymentTransactionDeleteDialog;
