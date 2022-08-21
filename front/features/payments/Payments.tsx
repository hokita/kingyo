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
} from '@chakra-ui/react'
import { AddIcon } from '@chakra-ui/icons'
import { FC } from 'react'
import { PaymentsState } from './paymentsSlice'
import useAppSelector from '../../common/hooks/useAppSelector'
import useAppDispatch from '../../common/hooks/useAppDispatch'

export const Table: FC = () => {
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

export const AddButton = () => {
  const dispatch = useAppDispatch()
  return (
    <IconButton
      colorScheme="blue"
      aria-label="Add Payment Buttun"
      icon={<AddIcon />}
    />
  )
}

export const DiscriptionForm: FC<{
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
}> = ({ onChange }) => {
  const dispatch = useAppDispatch()
  return (
    <FormControl>
      <FormLabel>Discription</FormLabel>
      <Input type="text" onChange={onChange} />
    </FormControl>
  )
}
