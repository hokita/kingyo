import {
  Table as Ctable,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Box,
  FormControl,
  FormLabel,
  FormErrorMessage,
  FormHelperText,
  Input,
  IconButton,
  Button,
} from '@chakra-ui/react'
import { AddIcon, DeleteIcon } from '@chakra-ui/icons'
import { useState, FunctionComponent } from 'react'
import { useRouter } from 'next/router'
import { PaymentsState } from './paymentsSlice'
import {
  createPayment,
  deletePayment,
} from '../../features/payments/paymentsSlice'
import useAppSelector from '../../common/hooks/useAppSelector'
import useAppDispatch from '../../common/hooks/useAppDispatch'

export const PaymentTable = () => {
  const { payments, loading } = useAppSelector((state) => state.payments)
  const dispatch = useAppDispatch()

  switch (loading) {
    case 'pending':
      return <div>loading...</div>
    case 'succeeded':
      return (
        <TableContainer>
          <Ctable variant="simple" size="sm" whiteSpace="pre">
            <Tbody>
              {payments.map((payment) => (
                <Tr key={payment.id}>
                  <Td>
                    {payment.paidAt.slice(0, 10)}
                    <br />
                    {payment.description}
                  </Td>
                  <Td>&yen;{payment.amount.toLocaleString()}</Td>
                  <Td>
                    <DeletePaymentButton id={payment.id} />
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Ctable>
        </TableContainer>
      )
    case 'failed':
      return <div>Failed. Try again later.</div>
    default:
      return <></>
  }
}

export const AddPaymentButton = () => {
  const dispatch = useAppDispatch()
  return (
    <IconButton
      colorScheme="blue"
      aria-label="Add Payment Buttun"
      icon={<AddIcon />}
    />
  )
}

const DeletePaymentButton: FunctionComponent<{ id: number }> = ({ id }) => {
  const dispatch = useAppDispatch()
  return (
    <IconButton
      aria-label="Delete Payment Buttun"
      icon={<DeleteIcon />}
      onClick={() => dispatch(deletePayment(id))}
    />
  )
}

export const NewPaymentForm = () => {
  const [description, setDiscription] = useState('')
  const [amount, setAmount] = useState('')
  const [paidAt, setPaidAt] = useState('')
  const dispatch = useAppDispatch()
  const { creating } = useAppSelector((state) => state.payments)
  const router = useRouter()

  switch (creating) {
    case 'succeeded':
      router.replace('/')
      break
  }

  const result = creating == 'failed' ? '失敗しました' : ''

  return (
    <>
      <Box border="1px" borderColor="gray.200" mb={3}>
        <FormControl>
          <FormLabel>Discription</FormLabel>
          <Input
            type="text"
            value={description}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setDiscription(e.target.value)
            }
          />
        </FormControl>
        <FormControl>
          <FormLabel>Amount</FormLabel>
          <Input
            type="number"
            value={amount}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setAmount(e.target.value)
            }
          />
        </FormControl>
        <FormControl>
          <FormLabel>Paid at</FormLabel>
          <Input
            type="date"
            value={paidAt}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setPaidAt(e.target.value)
            }
          />
        </FormControl>
      </Box>
      <Box>
        <Button
          colorScheme="blue"
          onClick={() =>
            dispatch(createPayment({ description, amount, paidAt }))
          }
        >
          Submit
        </Button>
      </Box>
      <Box>{result}</Box>
    </>
  )
}
