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
import { AddIcon } from '@chakra-ui/icons'
import { useState } from 'react'
import { PaymentsState } from './paymentsSlice'
import { createPayment } from '../../features/payments/paymentsSlice'
import useAppSelector from '../../common/hooks/useAppSelector'
import useAppDispatch from '../../common/hooks/useAppDispatch'

export const PaymentTable = () => {
  const { payments, loading } = useAppSelector((state) => state.payments)

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
                    {payment.paidAt}
                    <br />
                    {payment.description}
                  </Td>
                  <Td>&yen;{payment.amount.toLocaleString()}</Td>
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

export const NewPaymentForm = () => {
  const [description, setDiscription] = useState('')
  const [amount, setAmount] = useState(0)
  const [paidAt, setPaidAt] = useState('')
  const dispatch = useAppDispatch()

  return (
    <>
      <Box border="1px" borderColor="gray.200" mb={3}>
        <FormControl>
          <FormLabel>Discription</FormLabel>
          <Input
            type="text"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setDiscription(e.target.value)
            }
          />
        </FormControl>
        <FormControl>
          <FormLabel>Amount</FormLabel>
          <Input
            type="text"
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setAmount(parseInt(e.target.value))
            }
          />
        </FormControl>
        <FormControl>
          <FormLabel>Paid at</FormLabel>
          <Input
            type="date"
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
    </>
  )
}
