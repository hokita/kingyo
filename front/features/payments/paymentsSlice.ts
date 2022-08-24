import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { AppDispatch } from '../../app/store'
import axios from 'axios'

interface Payment {
  id: number
  description: String
  amount: number
  paidAt: String
  createdAt: String
  updatedAt: String
}

export interface PaymentsState {
  payments: Payment[]
  loading: 'idle' | 'pending' | 'succeeded' | 'failed'
  creating: 'idle' | 'pending' | 'succeeded' | 'failed'
}

const initialState = {
  payments: [],
  loading: 'idle',
  creating: 'idle',
} as PaymentsState

const paymentsSlice = createSlice({
  name: 'payments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(fetchPayments.pending, (state, action) => {
      state.loading = 'pending'
    })
    builder.addCase(fetchPayments.fulfilled, (state, action) => {
      state.payments = action.payload
      state.loading = 'succeeded'
    })
    builder.addCase(fetchPayments.rejected, (state, action) => {
      state.loading = 'failed'
    })
    builder.addCase(createPayment.fulfilled, (state, action) => {
      state.creating = 'succeeded'
    })
    builder.addCase(createPayment.rejected, (state, action) => {
      state.creating = 'failed'
    })
  },
})

export default paymentsSlice.reducer

// Thunk

export const fetchPayments = createAsyncThunk(
  'payments/getPayments',
  async () => {
    const result = await axios.get(
      `http://${process.env.NEXT_PUBLIC_SERVER_DOMAIN}/payments`
    )
    return result.data
  }
)

interface newPaymentForm {
  description: string
  amount: string
  paidAt: string
}

export const createPayment = createAsyncThunk(
  'payments/createPayment',
  async (form: newPaymentForm) => {
    const date = new Date(form.paidAt)
    const paidAt = date.toISOString().split('.')[0] + '+09:00'
    const body = {
      description: form.description,
      amount: parseInt(form.amount),
      paidAt,
    }
    const header = {
      'Content-Type': 'application/json',
    }
    await axios.post(
      `http://${process.env.NEXT_PUBLIC_SERVER_DOMAIN}/payments`,
      body,
      {
        headers: header,
      }
    )
  }
)
